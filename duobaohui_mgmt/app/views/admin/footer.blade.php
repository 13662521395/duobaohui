
      <!--footer start-->
      <!-- <footer class="site-footer">
          <div class="text-center">
              2015 &copy; 世和科技.
              <a href="#" class="go-top" title="返回顶部">
                  <i class="icon-angle-up"></i>
              </a>
          </div>
      </footer> -->
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    {{ HTML::script('/flatlib/js/bootstrap.min.js') }}
    {{ HTML::script('/flatlib/js/jquery.dcjqaccordion.2.7.js') }}
    {{ HTML::script('/flatlib/js/jquery.scrollTo.min.js') }}
    {{ HTML::script('/flatlib/js/jquery.nicescroll.js') }}
    {{ HTML::script('/flatlib/js/jquery.sparkline.js') }}
    {{ HTML::script('/flatlib/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js') }}
    {{ HTML::script('/flatlib/js/owl.carousel.js') }}
    {{ HTML::script('/flatlib/js/jquery.customSelect.min.js') }}
    {{ HTML::script('/flatlib/js/respond.min.js') }}

    {{ HTML::script('/flatlib/js/jquery.dcjqaccordion.2.7.js') }}

    <!--common script for all pages-->
    {{ HTML::script('/flatlib/js/common-scripts.js') }}

    <!--script for this page-->
    {{ HTML::script('/flatlib/js/sparkline-chart.js') }}
    {{ HTML::script('/flatlib/js/easy-pie-chart.js') }}
    {{ HTML::script('/flatlib/js/count.js') }}

  <script>

      //owl carousel

      $(document).ready(function() {
          $("#owl-demo").owlCarousel({
              navigation : true,
              slideSpeed : 100,
              paginationSpeed : 400,
              singleItem : true,
			  autoPlay:true

          });
      });

      //custom select box

      $(function(){
          $('select.styled').customSelect();
      });

  </script>

  </body>
</html>
