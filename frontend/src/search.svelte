<script lang="ts">
    import type {Task} from "./task";

    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

    let searchQuery = '';
    let searchResults: Task[] = [];
    let remainingSearchResults = 0;
    let pageLoadNumber = 0;
    let itemsPerPage = 10

    async function searchTasks(currentPageLoadNumber : number = 0) {
        let from = currentPageLoadNumber * itemsPerPage;
        let size = itemsPerPage;
        const response = await fetch(`${API_BASE_URL}/api/tasks/search?query=${searchQuery || ''}&from=${from}&size=${size}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            const searchResponse = await response.json();
            searchResults = searchResponse.tasks;
            remainingSearchResults = searchResponse.remaining;
        } else {
            console.error('Failed to search tasks');
        }
    }

    async function nextPage() {
        await searchTasks(++pageLoadNumber);
    }

    async function previousPage() {
        await searchTasks(--pageLoadNumber);
    }

    async function deleteTask(id : number) {
        const response = await fetch(`${API_BASE_URL}/api/tasks/${id}`, {
            method: 'DELETE',
        });
        if (response.ok) {
            searchResults = searchResults.filter(task => task.id !== id);
        } else {
            console.error('Failed to delete task');
        }
    }

    async function saveTask(task : Task) {
        const response = await fetch(`${API_BASE_URL}/api/tasks/${task.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({description: task.description})
        });
        if (response.ok) {
            const updatedTask = {...task, isEditing: false};
            searchResults = searchResults.map(t => t.id === task.id ? updatedTask : t);
        } else {
            console.error('Failed to update task');
        }
    }
</script>


<style>
    .flex-grow {
        flex-grow: 1;
    }
    
    .table {
        table-layout: fixed;
    }

    .no-wrap {
        white-space: nowrap;
    }

    .btn-width {
        width: 10rem;
    }

    .btn-width-small {
        width: 5rem;
    }
    .btn-margin {
        margin-right: 2rem;
    }
    .common-column {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .id-column {
        width: 5rem;
    }

    .description-column {
        width: 30rem;
    }

    .action-column {
        width: 11rem;
    }
</style>


<h5>Search tasks</h5>

<div class="d-flex justify-content-between">
    <input type="text" bind:value={searchQuery} placeholder="Search tasks" required class="form-control flex-grow">
    <button class="btn btn-primary ms-2 no-wrap btn-width" on:click={() => searchTasks(0)}>Search</button>
</div>

<table class="table table-dark table-striped my-sm-2">
    <thead>
    <tr>
        <th class="common-column id-column">ID</th>
        <th class="common-column description-column">Description</th>
        <th class="common-column action-column">Action</th>
    </tr>
    </thead>
    <tbody>
    {#each searchResults as task (task.id)}
        <tr>
            <td class="common-column id-column">{task.id}</td>
            <td class="common-column description-column">
                {#if task.isEditing}
                    <input type="text" bind:value={task.description} class="form-control">
                {:else}
                {/if}
                {task.description}
            </td>
            <td class="common-column action-column">
                {#if task.isEditing}
                    <button on:click={() => saveTask(task)} class="btn btn-width-small btn-success">Save</button>
                    <button on:click={() => task.isEditing = false} class="btn btn-width-small btn-warning">Cancel
                    </button>
                {:else}
                    <button on:click={() => task.isEditing = true} class="btn btn-width-small btn-primary">Edit</button>
                    <button on:click={() => deleteTask(task.id)} class="btn btn-width-small btn-danger">Delete</button>
                {/if}
            </td>
        </tr>
    {/each}
    </tbody>
</table>


<div class="d-flex justify-content-center">
    {#if pageLoadNumber > 0}
        <button class="btn btn-primary btn-width btn-margin" on:click={previousPage}>Previous page</button>
    {/if}
    {#if remainingSearchResults > 0}
        <button class="btn btn-primary btn-width btn-margin" on:click={nextPage}>Next page</button>
    {/if}
</div>


