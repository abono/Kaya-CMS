alter table media_history
add column media_id int;

update media_history
set media_id = (
  select media_id
  from media
  where media.path = media_history.path
);

alter table media_history
alter column media_id set not null;

