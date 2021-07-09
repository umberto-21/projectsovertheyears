<?php /* Template Name: About page */ ?>

<?php get_header(); ?>

<main class="container">
    <div class="row justify-content-start about-page-row-padding">
        <div class="col-sm-4 move-to-the-left-0"><span class="bold-style bigger">Work Experiences</span></div>
        <div class="col-sm-4 move-to-the-left-1 custom-text bg-rectangle bg-color-0">
            <span>October 2017 - present</span><br/>
            <span class="bold-style">Adobe Suite Online Teacher</span><br/>
            <span class="bold-style">JobFormazione.it</span><br/>
            <span>Lisbon</span><br/><br/><br />

            <span>January 2017 - present</span><br/>
            <span class="bold-style">Communication Designer</span><br/>
            <span class="bold-style">Jonny Mole Studio</span><br/>
            <span>Padua</span><br/><br/><br />

            <span>February - july 2016</span><br/>
            <span class="bold-style">Communication Intern</span><br/>
            <span class="bold-style">Palazzo Reale Museum</span><br/>
            <span>Milan</span><br/><br/><br />

            <span>2013-2016</span><br/>
            <span class="bold-style">Music Events Reviewer</span><br/>
            <span class="bold-style">Zero Milano Magazine</span><br/>
            <span>Milan</span>
        </div>
    </div>

    <div class="row justify-content-start">
        <div class="col-sm-4 move-to-the-left-3 display-on-mobile">
            <span class="bold-style bigger">Education</span>
        </div>

        <div class="col-sm-4 move-to-the-left-2 custom-text text-to-right bg-rectangle bg-color-1 bg-rectangle-orange">
            <span class="text-position" >2012 - 2016</span><br/>
            <span class="text-position bold-style ">Bachelor in Communication Design</span><br/>
            <span class="text-position bold-style">Politecnico di Milano</span><br/>
            <span class="text-position">Milan</span><br/><br/>

            <span class="text-position">January 2015 - June 2015</span><br/>
            <span class="text-position bold-style">Arts & Graphic Design</span><br/>
            <span class="text-position bold-style">University of Lapland</span><br/>
            <span class="text-position">Rovaniemi (FInland)</span><br/><br/>

            <span class="text-position">2007 - 2012</span><br/>
            <span class="text-position bold-style">High school qualification</span><br/>
            <span class="text-position bold-style">in classical studies</span><br/>
            <span class="text-position bold-style ">Liceo Giorgione</span><br/>
            <span class="text-position">Castelfranco V.to</span>

        </div>
        <div class="col-sm-4 move-to-the-left-3 not-display-on-mobile">
            <span class="bold-style bigger">Education</span>
        </div>
        <div class="col-sm-4 not-display-on-tablet">
            <img class="custom-image" src="<?php echo get_template_directory_uri(); ?>/images/kiko.png" alt="my profile photo"/>
        </div>
        <div class="col-sm-12 display-on-tablet">
            <img class="custom-image" src="<?php echo get_template_directory_uri(); ?>/images/kiko.png" alt="my profile photo"/>
        </div>

    </div>
    </div>
</main>

<?php get_footer(); ?>