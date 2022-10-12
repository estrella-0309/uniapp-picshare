<template>
	<view class="app">
		<view class="header">
			<view class="header-left">
				<view><text @click="flag=true" :class="underline">关注</text></view>
				<view><text @click="flag=false" :class="underline2">订阅</text></view>
			</view>
			<view class="header-right">
				<view>搜索</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flag:true
			}
		},
		onLoad(option) {		
				console.log(option);
		},
		methods: {

		},
		computed:{
			underline(){
				return this.flag?"active":"";
			},
			underline2(){
				return !this.flag?"active":"";
			}
		}
	}
</script>

<style scoped lang="scss">
	.active{
		border-bottom: #fff solid 3px;
		border-radius: 3px;
	}
	.app{
		.header{
			display: flex;
			width: 750rpx;
			height: 100rpx;
			background-color: #49ad88;
			line-height: 100rpx;
			color: #fff;
			font-size: 18px;
			.header-left{
				flex:9;
				display: flex;
				& view:nth-child(1){
					box-sizing:border-box;
					display: inline-block;
					text-align: right;
					flex:6;
				}
				& view:nth-child(2){
					margin-left: 100rpx;
					display: inline-block;
					flex:5;
				}
			}
			.header-right{
				flex:1;
				margin-right: 30rpx;
			}
		}
		
	}
</style>
