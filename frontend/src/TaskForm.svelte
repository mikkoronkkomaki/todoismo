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

<style>
    .flex-grow {
        flex-grow: 1;
    }
    .no-wrap {
        white-space: nowrap;
    }
    .btn-width {
        width: 200px;
    }
</style>

<form on:submit={submitForm}>
    <div class="d-flex justify-content-between">
        <input id="description" bind:value={description} required class="form-control flex-grow" placeholder="Task description">
        <button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Add Task</button>
    </div>
</form>