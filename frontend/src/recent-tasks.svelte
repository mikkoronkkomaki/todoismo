<script>
    import { recentTasksList } from './stores';
    $: recentTasks = [...$recentTasksList].reverse()
</script>

<h5>Recently added tasks</h5>

<table class="table table-dark  table-striped my-sm-2">
    <thead>
    <tr>
        <th>ID</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    {#each recentTasks as task (task.id)}
        <tr>
            <td>{task.id}</td>
            <td>{task.description}</td>
        </tr>
    {/each}
    </tbody>
</table>

