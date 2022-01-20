update web_page
set
  type_edits = :typeEdits,
  path_edits = :pathEdits,
  title_edits = :titleEdits,
  description_edits = :descriptionEdits,
  content_edits = :contentEdits,
  parameters_edits = :parametersEdits,
  modify_date = now(),
  web_page_template_id_edits = :webPageTemplateIdEdits
where web_page_id = :webPageId
