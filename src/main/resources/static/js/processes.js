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
                    var fileLink = $('<a></a>').text(file.processid).attr('href', '#');
                    fileLink.on('click', function() {
                        // When a file link is clicked, load the corresponding image
                        var imageContainer = $('#image-container');
                        var image = $('<img>').attr('src', '/images/' + file.processid + '/'+ file.processid +'.jpg');
                        imageContainer.empty().append(image);
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