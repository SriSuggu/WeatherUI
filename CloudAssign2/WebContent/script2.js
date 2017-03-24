$(document).ready(function(){
	
    $("button").click(function(){
    	var date1=document.getElementById("datevalue").value;
    	var formattedDate = new Date(date1);
     	formattedDate.setDate(formattedDate.getDate()+1);
     	console.log(formattedDate);
     	
     	
     	
     	var d = formattedDate.getDate();
     	var m =  formattedDate.getMonth();
     	m += 1;  // JavaScript months are 0-1
     	var y = formattedDate.getFullYear();
     	

     	date = y + "" + (m>9 ? '' : '0') + m + "" + (d>9 ? '' : '0') + d;
     	//console.log(date);
     		//date = y + "" + (m>9 ? '' : '0') + m + "" + (d+i>9 ? '' : '0') + (d+i);
     				
     
     	

   $.ajax({
      type:"GET",
      dataType:"json",
      async:false,
      url:"http://localhost:9090/CloudAssign2/rest/service/forecast/"+date,
      success:function(data){ 

      console.log(data);
      chartGen(data);

      }
      
      
      });


      $.ajax({
      type:"GET",
      dataType:"json",
      async:false,
      url:"http://localhost:9090/CloudAssign2/rest/service/forecast5/"+date,
      success:function(data){ 

      console.log(data);
      chartGen1(data);

      }
      
      
      });



    });
       
});    


function  chartGen(data)
{
Highcharts.chart('container', {
    chart: {
        type: 'spline'
    },
    title: {
        text: ' API Forecast Temperature'
    },
    subtitle: {
        
    },
    xAxis: {
        categories:[data[0].DATE,data[1].DATE,data[2].DATE,data[3].DATE,data[4].DATE]

    },
    yAxis: {
        title: {
            text: 'Temperature'
        },
        labels: {
            formatter: function () {
                return this.value + '�';
            }
        }
    },
    tooltip: {
        crosshairs: true,
        shared: true
    },
    plotOptions: {
        spline: {
            marker: {
                radius: 4,
                lineColor: '#666666',
                lineWidth: 1
            }
        }
    },
    series: [{
        name: 'TMIN',
        marker: {
            symbol: 'square'
        },
        data: [data[0].TMIN,{
            y: data[1].TMIN,
            marker: {
                symbol: 'url(https://www.highcharts.com/samples/graphics/snow.png)'
            }
        },data[2].TMIN, data[3].TMIN, data[4].TMIN]

    }, {
        name: 'TMAX',
        marker: {
            symbol: 'diamond'
        },
        data: [{
            y: data[0].TMAX,
            marker: {
                symbol: 'url(https://www.highcharts.com/samples/graphics/sun.png)'
            }
        }, data[1].TMAX, data[2].TMAX, data[3].TMAX, data[4].TMAX]
    }]
});
}


function  chartGen1(data)
{
Highcharts.chart('container1', {
    chart: {
        type: 'spline'
    },
    title: {
        text: '  Forecast'
    },
    subtitle: {
        text: 'Cincy weather'
    },
    xAxis: {
        categories:[data[0].DATE,data[1].DATE,data[2].DATE,data[3].DATE,data[4].DATE]

    },
    yAxis: {
        title: {
            text: 'Temperature'
        },
        labels: {
            formatter: function () {
                return this.value + '�';
            }
        }
    },
    tooltip: {
        crosshairs: true,
        shared: true
    },
    plotOptions: {
        spline: {
            marker: {
                radius: 4,
                lineColor: '#666666',
                lineWidth: 1
            }
        }
    },
    series: [{
        name: 'Temp MIN',
        marker: {
            symbol: 'square'
        },
        data: [data[0].TMIN,{
            y: data[1].TMIN,
            marker: {
                symbol: 'url(https://www.highcharts.com/samples/graphics/snow.png)'
            }
        },data[2].TMIN, data[3].TMIN, data[4].TMIN]

    }, {
        name: 'Temp Max',
        marker: {
            symbol: 'diamond'
        },
        data: [{
            y: data[0].TMAX,
            marker: {
                symbol: 'url(https://www.highcharts.com/samples/graphics/sun.png)'
            }
        }, data[1].TMAX, data[2].TMAX, data[3].TMAX, data[4].TMAX]
    }]
});
}
