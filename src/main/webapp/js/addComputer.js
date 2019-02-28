(function($) {
	$(document).on('submit', 'form', function() {		
		var introduced_input = $("#introduced")[0];
		var discontinued_input = $("#discontinued")[0];
		var name_input = $("#name")[0];
		
		var introduced = introduced_input.value;
		var discontinued = discontinued_input.value;
		var name = name_input.value;
		
		var introducedDate = new Date(introduced).getDate();
		var discontinuedDate = new Date(discontinued).getDate();
		
		$(introduced_input.closest('.form-group')).removeClass('has-error');
		$(discontinued_input.closest('.form-group')).removeClass('has-error');
		
		if ( name != "" ) {
			if ( isNaN(introducedDate) && !isNaN(discontinuedDate) ) {
				$(introduced_input.closest('.form-group')).addClass('has-error');
				$(discontinued_input.closest('.form-group')).addClass('has-error');
				return false;
			} else if ( introducedDate > discontinuedDate ) {
				$(introduced_input.closest('.form-group')).addClass('has-error');
				$(discontinued_input.closest('.form-group')).addClass('has-error');
				return false;
			}
		} else {
			$(name_input.closest('.form-group')).addClass('has-error');
			return false;
		}
		
		return true;
	});
}(jQuery));

function isValidDate(s) {
	var bits = s.split('/');
	var d = new Date(bits[2] + '/' + bits[1] + '/' + bits[0]);
	return !!(d && (d.getMonth() + 1) == bits[1] && d.getDate() == Number(bits[0]));
}