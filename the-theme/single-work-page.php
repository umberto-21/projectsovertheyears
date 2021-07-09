<?php
/*
 Template Name: single-work-page
 Template Post Type: post, page, my-work
 */
?>

<?php get_header (); ?>

<pre>
<?php

global $post;

function wp_get_attachment( $attachment_id ) {

    $attachment = get_post( $attachment_id );
    return array(
        'alt' => get_post_meta( $attachment->ID, '_wp_attachment_image_alt', true ),
        'caption' => $attachment->post_excerpt,
        'description' => $attachment->post_content,
        'href' => get_permalink( $attachment->ID ),
        'src' => $attachment->guid,
        'title' => $attachment->post_title
    );
}

function find_gallery($id)
{
    //load gallery by ID
    return FooGallery::get_by_id($id);

}

echo $post->ID;

$gallery_id = get_post_meta($post->ID, "the_gallery", true);
$gallery = find_gallery($gallery_id);

$slider_data = array();

foreach ($gallery->attachment_ids as $att_id) {

    array_push($slider_data, wp_get_attachment($att_id));

}

$placedate = get_post_meta($post->ID, "place&date", true);

?>
</pre>

<main class="container-fluid">

    <div class="row text-center align-items-center">
        <div class="col-sm-12">
            <span class="line-work-title"></span><br/>
            <span class="line-work-title"></span><br/>
            <span class="line-work-title"></span><br/>
            <span class="work-page-title"><?php echo get_the_title($post->ID); ?></span>
        </div>
    </div>

    <div>
        <div id="carouselControls" class="carousel slide" data-ride="carousel" data-interval = "false">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mv-text bold-style"><?php echo $slider_data [0]["title"]; ?></p><br/>
                            <p class="mv-text"><?php echo $placedate ?></p>
                        </div>
                        <div class="col-sm-6 align-items-center">
                            <img class="d-block img-fluid style-img" src="<?php echo $slider_data [0]["src"]; ?>" alt="<?php echo $slider_data [0]["title"]; ?>">
                        </div>
                        <div class="col-sm-3 mv-down">
                            <?php
                                echo $slider_data [0]["description"];
                            ?>
                        </div>
                    </div>
                </div>

                <?php
                for ($i =1; $i < count($slider_data); $i++) {

                    $title = $slider_data [$i]["title"];
                    $src = $slider_data [$i]["src"];
                    $content = $slider_data [$i]["description"];

                    echo '<div class="carousel-item">
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mv-text bold-style">'.$title.'</p>
                            <p class="mv-text">'.$placedate.'</p>
                        </div>
                        <div class="col-sm-6 align-items-center">
                            <img class="d-block img-fluid style-img" src='.$src.' alt='.$title.'>
                        </div>
                        <div class="col-sm-3 mv-down">'.$content.'</div>
                    </div>
                </div>';
                }

                ?>
            </div>
            <a class="carousel-control-prev" href="#carouselControls" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselControls" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>


</main>



<script>

    //$ = jQuery;

    $(function(){

        $(".description").empty().html($(".carousel-item.active").data("description"));



        $('#sliderProgetto').on('slide.bs.carousel', function (i,e) {

            console.log($(i.relatedTarget).data("description"));
            $(".description").empty().html($(i.relatedTarget).data("description"));

        });
    });
</script>

<?php get_footer (); ?>
