<?php

$presenter = new Illuminate\Pagination\BootstrapPresenter($paginator);

?>

<div class="text-center">
    <ul class="pagination pagination-lg">
        <?php echo $presenter->render(); ?>
    </ul>
</div>