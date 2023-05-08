CREATE TABLE IF NOT EXISTS product_image (id SERIAL, image BYTEA, PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS dispute_message (id SERIAL, message TEXT, sender CHARACTER VARYING(255), receiver CHARACTER VARYING(255), channel_id BIGINT, date TIMESTAMP(6) WITHOUT TIME ZONE, PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS dispute_channel (id SERIAL, name CHARACTER VARYING(255), unread INT, PRIMARY KEY (id));

CREATE OR REPLACE FUNCTION dispute_message_update_notify() RETURNS trigger AS
'
DECLARE
  payload TEXT;
BEGIN
  payload := json_build_object(''id'', NEW.id, ''message'', NEW.message, ''sender'', NEW.sender, ''receiver'', NEW.receiver, ''channelId'', NEW.channel_id, ''date'', TO_CHAR(NEW.date, ''YYYY-MM-DD HH24:MI''));
  PERFORM pg_notify(''dispute_message_notify'', payload);
  RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER dispute_message_trigger
    AFTER INSERT ON dispute_message
    FOR EACH ROW
    EXECUTE FUNCTION dispute_message_update_notify();

CREATE OR REPLACE FUNCTION dispute_channel_update_notify() RETURNS trigger AS
'
DECLARE
  payload TEXT;
BEGIN
  payload := json_build_object(''id'', NEW.id, ''name'', NEW.name, ''unread'', NEW.unread);
  PERFORM pg_notify(''dispute_channel_notify'', payload);
  RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER dispute_channel_trigger
    AFTER INSERT OR UPDATE ON dispute_channel
    FOR EACH ROW
    EXECUTE FUNCTION dispute_channel_update_notify();
