<script>
    import { onMount } from 'svelte';
    let searchQuery = '';
    let searchResults = [];

    async function searchTasks() {
        const response = await fetch(`http://localhost:8080/api/tasks/search?query=${searchQuery}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            searchResults = await response.json();
        } else {
            console.error('Failed to search tasks');
        }
    }

    async function deleteTask(id) {
        const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
            method: 'DELETE',
        });
        if (response.ok) {
            searchResults = searchResults.filter(task => task.id !== id);
        } else {
            console.error('Failed to delete task');
        }
    }
</script>

<div>
    <input type="text" bind:value={searchQuery} placeholder="Search tasks">
    <button on:click={searchTasks}>Search</button>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Description</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    {#each searchResults as task (task.id)}
        <tr>
            <td>{task.id}</td>
            <td>{task.description}</td>
            <td><button on:click={() => deleteTask(task.id)}>Delete</button></td>
        </tr>
    {/each}
    </tbody>
</table>