<script lang="ts">
    import {page} from "$app/stores";
    import {goto} from "$app/navigation";

    export let query: string;

    const search = () => {
        const newUrl = new URL($page.url.toString());
        newUrl.searchParams.set('query', query);
        newUrl.searchParams.set('currentPage', "0");
        goto(newUrl.toString());
    };

    const clearInput = () => {
        query = '';
    };
</script>

<h5>Search tasks</h5>

<form class="d-flex justify-content-between" on:submit|preventDefault={search}>
    <input type="text" bind:value={query} placeholder="Search tasks" class="form-control flex-grow">
    <button on:click={clearInput} class="btn btn-warning ms-2 no-wrap btn-width">Clear</button>
    <button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Search</button>
</form>