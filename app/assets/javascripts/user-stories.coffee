$ ->
  #api documentation http://developer.github.com/v3/issues/
  $.getJSON "https://api.github.com/repos/htw-docear/user-stories-proof-of-concept/issues?callback=?", {
   sort : "created"
   direction : "asc"
  }, (data) ->
    $.each data.data, (i,item) ->
      labels = (arrayItem.name for arrayItem in item.labels)
      toSelector= if "inprogress" in labels then "#scrum-inprogress" else "#scrum-open"
      $("<div class='well' style='border: solid 1px grey; margin-bottom: 16px;'><h4>" + item.title + "</h4>" + item.body + "</div>").appendTo(toSelector)
