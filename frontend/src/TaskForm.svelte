<script>
    import {onMount} from 'svelte';
    import {recentTasksList} from './stores';

    let description = '';

    const setNewRecentTask = (newTask) => {
        recentTasksList.update(tasks => {
            if (tasks.length >= 3) {
                tasks.shift(); // remove the first task
            }
            return [...tasks, newTask]; // add the new task
        });
    };

    async function submitForm(event) {
        event.preventDefault();
        const response = await fetch('http://localhost:8080/api/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({description})
        });
        if (response.ok) {
            const responseBody = await response.json();
            console.log('Task added', responseBody);
            setNewRecentTask(responseBody);
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

<h5>Add a new task</h5>

<form on:submit={submitForm}>
    <div class="d-flex justify-content-between">
        <input id="description" bind:value={description} required class="form-control flex-grow"
               placeholder="Task description">
        <button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Add Task</button>
    </div>
</form>