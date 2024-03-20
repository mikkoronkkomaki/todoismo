<script lang="ts">
    import {goto} from "$app/navigation";
    import {page} from "$app/stores";

    export let currentPage: number;
    export let remainingTasks: number;

    const gotoPage = (pageNumber: number) => {
        const newUrl = new URL($page.url.toString());
        newUrl.searchParams.set('currentPage', pageNumber.toString());
        goto(newUrl.toString());
    };

    const nextPage = () => {
        ++currentPage;
        gotoPage(currentPage);
    };

    const previousPage = () => {
        --currentPage;
        gotoPage(currentPage);
    }
</script>

<div class="d-flex justify-content-center">
    {#if currentPage > 0}
        <button class="btn btn-primary btn-width btn-margin" on:click={previousPage}>&#9664; Previous page</button>
    {/if}
    {#if remainingTasks > 0}
        <button class="btn btn-primary btn-width btn-margin" on:click={nextPage}>Next page &#9654;</button>
    {/if}
</div>
