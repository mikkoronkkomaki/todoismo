let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
interface LoadParams {
	url: {
		searchParams: URLSearchParams;
	};
}
export const load = async ({fetch, url: { searchParams } }: LoadParams) => {
	const query = searchParams.get('query') || '';
	const page = Number(searchParams.get('page')) || 0;
	const resultsPerPage = 10;

	let url = new URL(`${API_BASE_URL}/api/tasks/search`);
	let from  = (page * resultsPerPage).toString();
	let size = resultsPerPage.toString();

	url.searchParams.set('query', query);
	url.searchParams.set('from',  from);
	url.searchParams.set('size', size);

	let response = await fetch(url);
	
	if (!response.ok) {
		throw new Error(`HTTP error! status: ${response.status}`);
	}

	let data = await response.json();
	data.page = page;
	data.query = query;
	
	return data;
};
