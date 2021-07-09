<?php /* Template Name: Works page */ ?>

<?php get_header(); ?>

<main class="container">
    <div class="row">
        <?php
			$args =  array(
			        'post_type' => 'my-work',
					'orderby' => 'menu_order',
					'order' => 'ASC' );

			$custom_query = new WP_Query( $args );

			$contatore =0;
			$color = 0;

			while ($custom_query->have_posts()) : $custom_query->the_post();
            ?>
                <div class="col-sm-4">
                    <a href="<?php echo get_permalink(); ?>">
                        <div class="my-img-card" style="background-image: url('<?php the_post_thumbnail_url ();?>');    ">
                        </div>
                    </a>
                    <?php if ($contatore <3 && $color ===0) {
                        echo
                        '<span class="line-work line-color-0"></span>
                        <span class="line-work line-color-0"></span>
                        <span class="line-work line-color-0"></span>';
                        $contatore ++;
                        if ($contatore ===3) {
                            $contatore =0;
                            $color =1;
                        }
                    }
                    else if ($contatore <3 && $color ===1){
                        echo
                        '<span class="line-work line-color-1"></span>
                        <span class="line-work line-color-1"></span>
                        <span class="line-work line-color-1"></span>';
                        $contatore ++;
                        if ($contatore ===3) {
                            $contatore =0;
                            $color =0;
                        }
                    }
                    ?>

                </div>
        <?php
            endwhile;
        ?>
    </div>
</main>

<?php get_footer(); ?>
