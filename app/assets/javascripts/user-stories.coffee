$ ->
  #api documentation http://developer.github.com/v3/issues/
  # https://api.github.com/repos/htw-docear/user-stories-proof-of-concept/issues?sort=created&direction=asc
  $.getJSON "https://api.github.com/repos/htw-docear/user-stories-proof-of-concept/issues?callback=?", {
   sort : "created"
   direction : "asc"
  }, (data) ->
    $.each data.data, (i,item) ->
      console.log(item)
      labels = (arrayItem.name for arrayItem in item.labels)
      toSelector= if "inprogress" in labels then "#scrum-inprogress" else "#scrum-open"
      assignee = ""
      if item.assignee
        assignee = "<div class='assignee pull-right'>#{item.assignee.login} <img src='http://www.gravatar.com/avatar/#{item.assignee.gravatar_id}?s=50' /></div>"
        #http://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=50

      meta ='' #= "<div class='pull-left'>created at: #{item.created_at} <span class='badge'>#{item.comments}</span></div>"

      $("<div class='well user-story' style='border: solid 1px grey; margin-bottom: 16px;'><h4><a href='#{item.html_url}'>#{item.title}</a></h4><div class=body>#{item.body}</div><div class=footer>#{meta} #{assignee}</div></div>").appendTo(toSelector)
