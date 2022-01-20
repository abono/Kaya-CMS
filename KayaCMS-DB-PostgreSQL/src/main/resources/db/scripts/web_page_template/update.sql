update web_page_template
set
  name_edits = :nameEdits,
  content_edits = :contentEdits,
  modify_date = now()
where web_page_template_id = :webPageTemplateId
