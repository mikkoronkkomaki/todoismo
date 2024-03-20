import type {PageLoad} from "./$types"

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
let itemsPerPage = 10

async function searchTasks(currentPage: number = 0, size: number = 10, searchQuery: string = '') {
    let from = currentPage * itemsPerPage;
    try {
        const response = await fetch(`${API_BASE_URL}/api/tasks/search?query=${searchQuery || ''}&from=${from}&size=${size}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
    }
}

export const load: PageLoad = async ({url: {searchParams}}) => {
    const currentPage = Number(searchParams.get('currentPage')) || 0;
    const size = Number(searchParams.get('size')) || itemsPerPage;
    const query = searchParams.get('query') || "";
    const searchResults = await searchTasks(currentPage, size, query);

    return {
        currentPage,
        size,
        query,
        foundTasks: searchResults.tasks,
        remainingTasks: searchResults.remaining
    }
}



