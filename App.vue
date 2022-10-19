<script>
	export default {
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
	/*每个页面公共css */
	*{
		margin: 0;
		padding: 0;
	}
	div{
		box-sizing: border-box;
	}
	body{
		// background-color:#DFE0EF ;
		 // background-color:#409971 ;
	}
</style>
