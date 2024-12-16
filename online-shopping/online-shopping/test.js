document.addEventListener('DOMContentLoaded', function() {
    const contentDiv = document.getElementById('content');

    document.querySelectorAll('.nav-button').forEach(button => {
        button.addEventListener('click', function() {
            const page = this.getAttribute('data-page');
            fetchDataAndRender(page);
        });
    });

    function fetchDataAndRender(page) {
        fetch(`/api/${page}`)
            .then(response => response.json())
            .then(data => {
                renderContent(data);
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function renderContent(data) {
        contentDiv.innerHTML = ''; // Clear previous content
        // Assuming data is an array of items to display
        data.forEach(item => {
            const itemElement = document.createElement('div');
            itemElement.textContent = item.name; // Customize based on your data structure
            contentDiv.appendChild(itemElement);
        });
    }
});
