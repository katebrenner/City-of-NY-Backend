create table ACCIDENTS (
  ID serial,
  DATE varchar(100),
  TIME varchar(100),
  BOROUGH VARCHAR(100),
  ZIP_CODE VARCHAR(100),
  Latitude FLOAT,
  LONGITUDE FLOAT
);

ALTER TABLE ACCIDENTS
  ADD NOTES varchar(255);

ALTER TABLE ACCIDENTS
  ADD number_of_persons_injured INT;

ALTER TABLE ACCIDENTS
  ADD number_of_persons_killed INT;