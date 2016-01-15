<html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
		<#if registeredProductList??>
			var data = google.visualization.arrayToDataTable([
				          ['Product Name', 'No.of Registered Product'],
				          <#list registeredProductList as registeredproduct>['${registeredproduct.productTemplateName}',${registeredproduct.numberofRegisteredProduct}],</#list>
				          ['Remaining Product Record', ${remainingRecordCount}]
		        ]);
		</#if>
        var options = {
          title: 'Registered Product Details'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="piechart" style="width: 900px; height: 500px;"></div>
    <#list registeredProductList as registeredproduct>
				          ${registeredproduct.productTemplateName}--->${registeredproduct.numberofRegisteredProduct}<br>
	</#list>
	<div id="map" style="width: 500px; height: 400px;"></div>
  
	  <script>
    // Define your locations: HTML content for the info window, latitude, longitude
    var locations = [
    	<#list registeredProductList as registeredProductVO>
    		<#list registeredProductVO.appUserDetailsList as appuserdetaillistvo>
      ['<h4>${registeredProductVO.productTemplateName}</h4>', ${appuserdetaillistvo.latitude}, ${appuserdetaillistvo.longitude}, '${registeredProductVO.iconImg}'],
     		</#list>
     	</#list>
    	];
    
    // Setup the different icons and shadows
    /*var iconURLPrefix = 'http://maps.google.com/mapfiles/ms/icons/';
    
    var icons = [
      iconURLPrefix + 'red-dot.png',
      iconURLPrefix + 'green-dot.png',
      iconURLPrefix + 'blue-dot.png',
      iconURLPrefix + 'orange-dot.png',
      iconURLPrefix + 'purple-dot.png',
      iconURLPrefix + 'pink-dot.png',      
      iconURLPrefix + 'yellow-dot.png'
    ]
    var iconsLength = icons.length;*/

    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: new google.maps.LatLng(-37.92, 151.25),
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      mapTypeControl: false,
      streetViewControl: false,
      panControl: false,
      zoomControlOptions: {
         position: google.maps.ControlPosition.LEFT_BOTTOM
      }
    });

    var infowindow = new google.maps.InfoWindow({
      maxWidth: 160
    });

    var markers = new Array();
    
    //var iconCounter = 0;
    
    // Add the markers and infowindows to the map
    for (var i = 0; i < locations.length; i++) {  
      var marker = new google.maps.Marker({
        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        map: map,
        icon: locations[i][3]
      });

      markers.push(marker);

      google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(locations[i][0]);
          infowindow.open(map, marker);
        }
      })(marker, i));
      
      //iconCounter++;
      // We only have a limited number of possible icon colors, so we may have to restart the counter
      /*if(iconCounter >= iconsLength) {
      	iconCounter = 0;
      }*/
    }

    function autoCenter() {
      //  Create a new viewpoint bound
      var bounds = new google.maps.LatLngBounds();
      //  Go through each...
      for (var i = 0; i < markers.length; i++) {  
				bounds.extend(markers[i].position);
      }
      //  Fit these bounds to the map
      map.fitBounds(bounds);
    }
    autoCenter();
  </script> 
  </body>
</html>