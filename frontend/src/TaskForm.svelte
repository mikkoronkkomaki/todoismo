<script>
    import { onMount } from 'svelte';
    let description = '';

    async function submitForm(event) {
        event.preventDefault();
        const response = await fetch('http://localhost:8080/api/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ description })
        });
        if (response.ok) {
            description = '';
        } else {
            console.error('Failed to add task');
        }
    }
</script>

<form on:submit={submitForm}>
    <label for="description">Description:</label>
    <input id="description" bind:value={description} required>
    <button type="submit">Add Task</button>
</form>