<script lang="ts">
    import type {Task} from "./task";

    export let task: Task;

    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

    async function deleteTask(id: number) {
        const response = await fetch(`${API_BASE_URL}/api/tasks/${id}`, {
            method: 'DELETE',
        });
        if (response.ok) {
            //       searchResults = searchResults.filter(task => task.id !== id);
        } else {
            console.error('Failed to delete task');
        }
    }

    async function saveTask(task: Task) {
        const response = await fetch(`${API_BASE_URL}/api/tasks/${task.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({description: task.description})
        });
        if (response.ok) {
            //     searchResults = searchResults.map(t => t.id === task.id ? updatedTask : t);
        } else {
            console.error('Failed to update task');
        }
    }
</script>

<tr>
    <td class="common-column id-column">{task.id}</td>
    <td class="common-column description-column">
        {#if task.isEditing}
            <input type="text" bind:value={task.description} class="form-control">
        {:else}
            {task.description}
        {/if}
    </td>
    <td class="common-column action-column">
        {#if task.isEditing}
            <button on:click={() => {saveTask(task); task.isEditing = false;}} class="btn btn-width-small btn-success">
                Save
            </button>
            <button on:click={() => task.isEditing = false} class="btn btn-width-small btn-warning">Cancel
            </button>
        {:else}
            <button on:click={() => task.isEditing = true} class="btn btn-width-small btn-primary">Edit</button>
            <button on:click={() => {deleteTask(task.id);task.isEditing = false;}}
                    class="btn btn-width-small btn-danger">Delete
            </button>
        {/if}
    </td>
</tr>
