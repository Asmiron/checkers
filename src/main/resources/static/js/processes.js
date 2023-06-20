$(document).ready(function() {
    // Make a POST request to the server to retrieve the file list
    $.ajax({
        url: '/processes/index', // Replace with your server endpoint
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            // Handle the response
            if (response && Array.isArray(response)) {
                var fileList = response;
                var listContainer = $('#file-list');

                // Create list items for each file
                fileList.forEach(function(file) {
                    var listItem = $('<li></li>');
                    var fileLink = $('<a></a>').text(file.processid + " Create date: "+ new Date().toLocaleString("ru", file.init_date)).attr('href', '#');
                    var id=file.processid
                    fileLink.on('click', function() {
                        // When a file link is clicked, load the corresponding image
                        var imageContainer = $('#image-container');
                        var url = `/processes/${id}`;
                        fetch(url)
                            .then(response => response.json()) // Преобразование ответа в JSON
                            .then(data => {
                                var base64Image = data.image; // Предполагается, что в ответе есть поле "image" с кодом base64
                                var image = new Image();
                                image.src = 'data:image/jpg;base64,' + base64Image; // Устанавливаем источник изображения
                                imageContainer.empty().append(image);
                                var fileJson = $('<label_JS></label_JS>').text(JSON.stringify(data.json_data, null, 2));
                                imageContainer.append('<br>'); // Добавляем перевод строки
                                imageContainer.append(fileJson);
                            })
                            .catch(error => {
                                console.error('Ошибка при обработке изображения:', error);
                            });
                    });
                    listItem.append(fileLink);
                    listContainer.append(listItem);
                });
            }
        },
        error: function() {
            alert('Error retrieving file list');
        }
    });
});