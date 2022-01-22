update media
set
  type_edits = :typeEdits,
  path_edits = :pathEdits,
  content_edits = :contentEdits,
  modify_date = now()
where media_id = :mediaId
