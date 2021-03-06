<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <link href="/static/bootstrap.min.css" rel="stylesheet"/>
    <title>ECharts</title>
    <style type="text/css">
.row{
	margin: 15px 0;
}
    </style>
</head>


<body class="container">
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
  <div class="row">
    	<a class="btn btn-primary" href="/">测试一</a>
    	<a class="btn btn-primary" href="?test=1">测试二</a>
    </div>
    
    <c:if test="${param.test eq 1 }">
    <div class="row">
    	<form action="" method="get" class="form-inline">
    		<input type="hidden" name="test" value="1"/>
    		<input class="form-control" name="sRna" placeholder="在这里输入miRna" value="${sRna }"/>
    		<input class="form-control" name="sDisease" placeholder="在这里输入疾病" value="${sDisease }"/>
    		<button class="btn btn-primary">Go</button>
    	</form>
    </div>
    </c:if>
    
    <div id="main" style="height:800px"></div>
     <!-- ECharts单文件引入 -->
    <script src="/static/echarts.js"></script>
    
    <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/force' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                
                var option = {
                	    title : {
                	        text: 'miRNA与疾病的关系',
                	        subtext: '数据来自...',
                	        x:'right',
                	        y:'bottom'
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: '{a} : {b}'
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            restore : {show: true},
                	            magicType: {show: true, type: ['force', 'chord']},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    legend: {
                	        x: 'left',
                	        data:['miRNA','疾病']
                	    },
                	    series : [
                	        {
                	            type:'force',
                	            name : "节点",
                	            ribbonType: false,
                	            categories : [
                	                {
                	                    name: 'miRNA'
                	                },
                	                {
                	                    name: '疾病'
                	                }
                	            ],
                	            itemStyle: {
                	                normal: {
                	                    label: {
                	                        show: true,
                	                        textStyle: {
                	                            color: '#333'
                	                        }
                	                    },
                	                    nodeStyle : {
                	                        brushType : 'both',
                	                        borderColor : 'rgba(255,215,0,0.4)',
                	                        borderWidth : 1,
                	                    },
                	                    linkStyle: {
                	                        type: 'line'
                	                    }
                	                },
                	                emphasis: {
                	                    label: {
                	                        show: false
                	                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                	                    },
                	                    nodeStyle : {
                	                        //r: 30
                	                    },
                	                    linkStyle : {}
                	                }
                	            },
                	            useWorker: false,
                	            minRadius : 15,
                	            maxRadius : 25,
                	            gravity: 1.1,
                	            scaling: 1.1,
                	            roam: 'move',
                	            nodes:[
                	                /* {category:0, name: '乔布斯', value : 10,symbol:'diamond'},
                	                {category:1, name: '丽萨-乔布斯',value : 2,symbol:'triangle'},
                	                {category:1, name: '保罗-乔布斯',value : 3 ,symbol:'heart'},
                	                {category:1, name: '克拉拉-乔布斯',value : 3,symbol:'droplet'},
                	                {category:1, name: '劳伦-鲍威尔',value : 7,symbol:'pin'},
                	                {category:0, name: '史蒂夫-沃兹尼艾克',value : 5,symbol:'star'},
                	                {category:0, name: '奥巴马',value : 8},
                	                {category:0, name: '比尔-盖茨',value : 9},
                	                {category:0, name: '乔纳森-艾夫',value : 4},
                	                {category:0, name: '蒂姆-库克',value : 4},
                	                {category:0, name: '龙-韦恩',value : 1}, */
                	                <c:forEach items="${rna }" var="i">
                	                {category:0, label: '${i.rna }', symbol:'diamond',name:'rna-${i.id}'},
                	                </c:forEach>
                	                <c:forEach items="${disease }" var="i">
                	                {category:1, label: '${i.disease }',symbol:'circle',name:'disease-${i.id}'},
                	                </c:forEach>
                	            ],
                	            links : [
									<c:forEach items="${rel }" var="i">
									{source : 'rna-${i.rna}', target : 'disease-${i.disease}', weight : 1, name: '关联'},
									</c:forEach>
                	                /* {source : '丽萨-乔布斯', target : '乔布斯', weight : 1, name: '女儿'},
                	                {source : '保罗-乔布斯', target : '乔布斯', weight : 2, name: '父亲'},
                	                {source : '克拉拉-乔布斯', target : '乔布斯', weight : 1, name: '母亲'},
                	                {source : '劳伦-鲍威尔', target : '乔布斯', weight : 2},
                	                {source : '史蒂夫-沃兹尼艾克', target : '乔布斯', weight : 3, name: '合伙人'},
                	                {source : '奥巴马', target : '乔布斯', weight : 1},
                	                {source : '比尔-盖茨', target : '乔布斯', weight : 6, name: '竞争对手'},
                	                {source : '乔纳森-艾夫', target : '乔布斯', weight : 1, name: '爱将'},
                	                {source : '蒂姆-库克', target : '乔布斯', weight : 1},
                	                {source : '龙-韦恩', target : '乔布斯', weight : 1},
                	                {source : '克拉拉-乔布斯', target : '保罗-乔布斯', weight : 1},
                	                {source : '奥巴马', target : '保罗-乔布斯', weight : 1},
                	                {source : '奥巴马', target : '克拉拉-乔布斯', weight : 1},
                	                {source : '奥巴马', target : '劳伦-鲍威尔', weight : 1},
                	                {source : '奥巴马', target : '史蒂夫-沃兹尼艾克', weight : 1},
                	                {source : '比尔-盖茨', target : '奥巴马', weight : 6},
                	                {source : '比尔-盖茨', target : '克拉拉-乔布斯', weight : 1},
                	                {source : '蒂姆-库克', target : '奥巴马', weight : 1} */
                	            ]
                	        }
                	    ]
                	};
                	var ecConfig = require('echarts/config');
                	function focus(param) {
                	    var data = param.data;
                	    var links = option.series[0].links;
                	    var nodes = option.series[0].nodes;
                	    if (
                	        data.source !== undefined
                	        && data.target !== undefined
                	    ) { //点击的是边
                	        var sourceNode = nodes.filter(function (n) {return n.name == data.source})[0];
                	        var targetNode = nodes.filter(function (n) {return n.name == data.target})[0];
                	        console.log("选中了边 " + sourceNode.name + ' -> ' + targetNode.name + ' (' + data.weight + ')');
                	    } else { // 点击的是点
                	        console.log(data);
                	    	if(data.category == 0){
                	    		location.href="?sRna="+data.label;
                	    	}
                	    	else if(data.category == 1){
                	    		location.href="?sDisease="+data.label;
                	    	}
                	    }
                	}
                	myChart.on(ecConfig.EVENT.CLICK, focus)

                	myChart.on(ecConfig.EVENT.FORCE_LAYOUT_END, function () {
                	    console.log(myChart.chart.force.getPosition());
                	});

                	 // 为echarts对象加载数据 
                    myChart.setOption(option); 
                	                    
            }
        );
    </script>
</body>