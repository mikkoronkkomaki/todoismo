<script lang="ts">
    import TaskRow from "./task-row.svelte";
    import type {Task} from "./task";
    export let foundTasks: Task[];
</script>

<table class="table table-dark table-striped my-sm-2">
    <thead>
    <tr>
        <th class="common-column id-column">ID</th>
        <th class="common-column description-column">Description</th>
        <th class="common-column action-column">Action</th>
    </tr>
    </thead>
    <tbody>
    {#each foundTasks as task (task.id)}
        <TaskRow {task}></TaskRow>
    {/each}
    </tbody>
</table>