
					<ul class="pagination pagination-lg">
						@if ( $pageInfo->page > 0 )
						<li><a href="?page={{ $pageInfo->page - 1 }}">«</a></li>
						@endif
						<li><a href="#">{{ $pageInfo->page + 1 }}</a></li>
						@if ( $pageInfo->page < $pageInfo->totalPage )
						<li><a href="?page={{ $pageInfo->page + 1 }}">»</a></li>
						@endif
					</ul>
