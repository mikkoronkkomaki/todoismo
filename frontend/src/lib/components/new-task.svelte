<script lang="ts">
    import {enhance} from '$app/forms';
</script>

<form use:enhance method="post" action="?/create">
    <div class="d-flex justify-content-between">
        <input required type="text" name="description" placeholder="Task description" class="form-control flex-grow"/>
        <button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Add Task</button>
    </div>
</form>