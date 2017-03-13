-- :name create-to_do :! :n
-- :doc creates a new user record
INSERT INTO to_dos
(description, completed)
VALUES (:description, :completed)

-- :name update-to_do :! :n
-- :doc update an existing user record
UPDATE to_dos
SET description = :description, completed = :completed
WHERE id = :id

-- :name get-to_do :? :1
-- :doc retrieve a user given the id.
SELECT * FROM to_do
WHERE id = :id

-- :name delete-to_do :! :n
-- :doc delete a user given the id
DELETE FROM to_dos
WHERE id = :id

-- :name get-to_dos :? :*
-- :doc get all to_dos
SELECT * FROM to_dos