function validateSubmitTeam(form)
{
    $('.form_error').each(function() {
        $(this).removeClass('form_error')
    });
    
    if ($('#lagnamn', form).val().length == 0)
    {
        $('#lagnamn', form).addClass('form_error');
        $.fancybox('Du måste ange ett lagnamn.')
        return false;
    }
    
    if ($('#email', form).val().length == 0 || checkEmail($('#email', form)) == false)
    {
        $('#email', form).addClass('form_error');
        $.fancybox('Du måste ange en e-postadress.')
        return false;
    }
    
    if ($('#stad', form).val().length == 0)
    {
        $('#stad', form).addClass('form_error');
        $.fancybox('Du måste ange vilken stad ni kommer ifrån.')
        return false;
    }
    
    var oneName = false;
    var onePnum = false;
    var allPnumOk = true;
    $('.mem_name').each(function() {
        if ($(this).val().length > 0)
            oneName = true;
    });
    $('.mem_pnum').each(function() {
        if ($(this).val().length > 0)
        {
            onePnum = true;
            
            if (!check_pNumber($(this)))
            {
            	allPnumOk = false;
            }
        }
    });
    
    if (!(oneName && onePnum && allPnumOk))
    {
        $.fancybox('Du måste ange minst en lagmedlem.')
        return false;
    }
    
    if (!$('#confirm').attr('checked'))
    {
        $.fancybox('Du måste godkänna villkåren.')
        return false;
    }
    
    return true;
}



function check_pNumber(me)
{
	var nr = $(me).val();
	nr = nr.replace(/[^\d]/g, '');
	var l = nr.length;
	
	if (l != 12)
	{
		$(me).addClass('form_error');
		$.fancybox('Ange personnummer med 12 siffror på formatet ÅÅÅÅMMDD-XXXX')
		return false;
	}
	
	totSum = 0;
	sum = 0;
	
	for (var i = 2; i < nr.length - 1; i++)
	{
		sum = parseInt(nr.substr(i, 1));
		if ((i % 2) == 0)
		{
			sum = parseInt(nr.substr(i, 1)) * 2;
			if (sum.toString().length > 1)
			{
				sum = parseInt(sum.toString().substr(0, 1)) + parseInt(sum.toString().substr(1, 1));
			}
		}
		
		totSum = totSum + sum;
	}
	
	check = 0;
	check = totSum.toString().substr(totSum.toString().length - 1);
	if (check != 0)
	{
		check = 10 - check;
	}
	if (check != nr.substr(nr.length - 1))
	{
		$(me).addClass('form_error');
		$.fancybox('Ogiltligt personnummer');
		return false;
	}
	
	return true;
}

function checkEmail(me) {
	var email = $(me).val();
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	if (!filter.test(email)) {
		$(me).addClass('form_error');
		$.fancybox('Ange en giltlig e-postadress');
		return false;
	}

	return true;
}
