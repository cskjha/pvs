jQuery(document).ready(function($){
						$('.my-form .add-box').click(function(){
							var n = $('.text-box').length + 1;
								if( 50 < n ) {
								alert('Limit 50!');
								return false;
						}
						var box_html = $('<p class="text-box"><label for="Box' + n + '">Field <span class="box-number">' + n + '</span></label> <input type="text" placeholder="Field Name" name="field'+n+'" value="" id="box' + n + '" /><input type="button" value=" Remove " class="remove-box" /> </p>');
						box_html.hide();
						$('.my-form p.text-box:last').after(box_html);
						box_html.fadeIn('slow');
						return false;
					});
			$('.my-form').on('click', '.remove-box', function(){
				$(this).parent().fadeOut("slow", function() {
					$(this).remove();
					$('.box-number').each(function(index){
						$(this).text( index + 1 );
					});
				});
				return false;
			});
		});