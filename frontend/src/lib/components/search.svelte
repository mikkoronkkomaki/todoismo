<script lang="ts">
	export let query: string = '';
</script>

<form class="d-flex justify-content-between">
	<input type="search" placeholder="Search by description" name="query" value={query} class="form-control flex-grow"/>
	<button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Search</button>
</form>
