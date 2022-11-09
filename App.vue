<script>
	export default {
		globalData:{
			ApiCount:0
		},
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
			
		}
	}
</script>

<style lang="scss">
	@import 'uview-ui/index.scss';
	*{
		margin: 0;
		padding: 0;
	}
	div{
		box-sizing: border-box;
	}
	body{
	}
</style>
