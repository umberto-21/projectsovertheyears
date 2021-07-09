function closeModal() {
    document.getElementById('myModal').style.display = "none";
}



// Get the image and insert it inside the modal - use its "alt" text as a caption
function openModal(element){
    // Get the modal
    var modal = document.getElementById('myModal');

    modal.style.display = "block";

    var modalImg = document.getElementById("img0");
    modalImg.src = element.src;

    var captionText = document.getElementById("caption");
    captionText.innerHTML = element.alt;
}
