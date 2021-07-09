<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Federico Zoppei</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="<?php bloginfo('stylesheet_url');?>" rel="stylesheet">

    <?php wp_enqueue_script("jquery"); ?>
    <?php wp_head(); ?>
</head>

<body>

<nav class="navbar fixed-top navbar-expand-sm navbar-light bg-white">
    <a class="navbar-brand" href="<?php echo esc_url( home_url( '/' ) ); ?>">
        <img class="my-logo" src="<?php echo get_template_directory_uri(); ?>/images/logo-federicozoppei-0.svg" alt="Federico Zoppei"/>
    </a>
    <a class="navbar-brand ml-auto" href="<?php echo esc_url( home_url( '/?page_id=219' ) ); ?>">Works</a>
</nav>

<nav class="navbar fixed-bottom navbar-light bg-white">
    <a class="navbar-brand" href="<?php echo esc_url( home_url( '/?page_id=9' ) ); ?>">Contacts</a>
    <a class="navbar-brand ml-auto" href="<?php echo esc_url( home_url( '/?page_id=2' ) ); ?>">About</a>
</nav>