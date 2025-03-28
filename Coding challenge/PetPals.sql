CREATE DATABASE IF NOT EXISTS PetPals;

USE PetPals;

CREATE TABLE IF NOT EXISTS Shelters (
    ShelterID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Pets (
    PetID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Age INT NOT NULL,
    Breed VARCHAR(100) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    AvailableForAdoption BIT DEFAULT 1,
    OwnerID INT DEFAULT NULL,
    ShelterID INT,
    FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID) ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS Donations (
    DonationID INT PRIMARY KEY AUTO_INCREMENT,
    ShelterID INT,
    DonorName VARCHAR(100) NOT NULL,
    DonationType ENUM('Cash', 'Item') NOT NULL,
    DonationAmount DECIMAL(10,2),
    DonationItem VARCHAR(255),
    DonationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS AdoptionEvents (
    EventID INT PRIMARY KEY AUTO_INCREMENT,
    EventName VARCHAR(100) NOT NULL,
    EventDate DATETIME NOT NULL,
    Location VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS Participants (
    ParticipantID INT PRIMARY KEY AUTO_INCREMENT,
    ParticipantName VARCHAR(100) NOT NULL,
    ParticipantType ENUM('Shelter', 'Adopter') NOT NULL,
    EventID INT,
    FOREIGN KEY (EventID) REFERENCES AdoptionEvents(EventID) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Adoptions (
    AdoptionID INT PRIMARY KEY AUTO_INCREMENT,
    PetID INT NOT NULL,
    ParticipantID INT NOT NULL,  -- The adopter's participant ID
    AdoptionDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (PetID) REFERENCES Pets(PetID) ON DELETE CASCADE,
    FOREIGN KEY (ParticipantID) REFERENCES Participants(ParticipantID) ON DELETE CASCADE
);


DELIMITER //
CREATE TRIGGER After_Adoption
AFTER INSERT ON Adoptions
FOR EACH ROW
BEGIN
    UPDATE Pets
    SET OwnerID = NEW.ParticipantID, AvailableForAdoption = 0
    WHERE PetID = NEW.PetID;
END;
//
DELIMITER ;

USE PetPals;

INSERT INTO Shelters (Name, Location) VALUES
('Paws Haven', 'Mumbai, Maharashtra'),
('Care for Paws', 'Bangalore, Karnataka'),
('Happy Tails Shelter', 'Chennai, Tamil Nadu'),
('Stray Rescue India', 'Delhi'),
('Pawfect Home', 'Hyderabad, Telangana'),
('Love & Care Animal Shelter', 'Pune, Maharashtra'),
('Helping Hands for Animals', 'Kolkata, West Bengal'),
('Furry Friends Foundation', 'Ahmedabad, Gujarat'),
('Animal Aid Society', 'Jaipur, Rajasthan'),
('Rescue Paws Trust', 'Lucknow, Uttar Pradesh');

INSERT INTO Pets (Name, Age, Breed, Type, AvailableForAdoption, ShelterID) VALUES
('Sheru', 3, 'Indian Pariah', 'Dog', 1, 1),
('Milo', 2, 'Labrador Retriever', 'Dog', 1, 2),
('Ginger', 4, 'Persian', 'Cat', 1, 3),
('Tommy', 5, 'Golden Retriever', 'Dog', 1, 4),
('Simba', 1, 'Siamese', 'Cat', 1, 5),
('Bruno', 2, 'Doberman', 'Dog', 1, 6),
('Cleo', 3, 'Indian Stray', 'Cat', 1, 7),
('Buddy', 6, 'Beagle', 'Dog', 1, 8),
('Luna', 2, 'Husky', 'Dog', 1, 9),
('Rocky', 4, 'German Shepherd', 'Dog', 1, 10);


SET SQL_SAFE_UPDATES = 0;

ALTER TABLE Donations ADD COLUMN DonationMonthYear VARCHAR(20);

INSERT INTO Donations (ShelterID, DonorName, DonationType, DonationAmount, DonationItem) VALUES
(1, 'Rahul Sharma', 'Cash', 5000, NULL),
(2, 'Amit Kumar', 'Item', NULL, 'Dog food, Blankets'),
(3, 'Sneha Mehta', 'Cash', 7500, NULL),
(4, 'Neha Verma', 'Item', NULL, 'Cat food, Litter box'),
(5, 'Anil Kapoor', 'Cash', 10000, NULL),
(6, 'Priya Singh', 'Item', NULL, 'Animal medicines'),
(7, 'Vikram Raj', 'Cash', 12000, NULL),
(8, 'Rohit Das', 'Item', NULL, 'Dog collars, Toys'),
(9, 'Swati Gupta', 'Cash', 6500, NULL),
(10, 'Deepak Reddy', 'Item', NULL, 'Cat scratchers, Beds');

UPDATE Donations
SET DonationMonthYear = DATE_FORMAT(DonationDate, '%M %Y');


INSERT INTO AdoptionEvents (EventName, EventDate, Location) VALUES
('Paw Adoption Drive', '2025-04-10 10:00:00', 'Mumbai, Maharashtra'),
('Furry Friends Fest', '2025-05-15 11:00:00', 'Bangalore, Karnataka'),
('Adopt a Pet Day', '2025-06-20 12:00:00', 'Chennai, Tamil Nadu'),
('Paws & Hugs', '2025-07-25 14:00:00', 'Delhi'),
('Stray Rescue Meet', '2025-08-30 09:30:00', 'Hyderabad, Telangana'),
('Love for Paws', '2025-09-12 10:00:00', 'Pune, Maharashtra'),
('Rescue & Rehome', '2025-10-18 11:30:00', 'Kolkata, West Bengal'),
('Forever Home Festival', '2025-11-22 13:00:00', 'Ahmedabad, Gujarat'),
('Pet Lovers Meet', '2025-12-05 15:00:00', 'Jaipur, Rajasthan'),
('Joy of Adoption', '2026-01-10 16:30:00', 'Lucknow, Uttar Pradesh');

INSERT INTO Participants (ParticipantName, ParticipantType, EventID) VALUES
('Paws Haven', 'Shelter', 1),
('Care for Paws', 'Shelter', 2),
('Sneha Verma', 'Adopter', 3),
('Happy Tails Shelter', 'Shelter', 4),
('Arjun Patel', 'Adopter', 5),
('Pawfect Home', 'Shelter', 6),
('Nidhi Sharma', 'Adopter', 7),
('Helping Hands for Animals', 'Shelter', 8),
('Rahul Mishra', 'Adopter', 9),
('Rescue Paws Trust', 'Shelter', 10);

INSERT INTO Adoptions (PetID, ParticipantID, AdoptionDate) VALUES
(1, 3, '2025-04-12 12:00:00'),
(2, 5, '2025-05-18 14:00:00'),
(3, 7, '2025-06-22 16:00:00'),
(4, 9, '2025-07-28 11:00:00'),
(5, 3, '2025-08-31 10:30:00'),
(6, 5, '2025-09-14 09:00:00'),
(10, 5, '2026-01-15 13:20:00');


-- 5th Query
SELECT Name, Age, Breed, Type 
FROM Pets
WHERE AvailableForAdoption = 1;

-- 6th Query
SELECT p.ParticipantName, p.ParticipantType
FROM Participants p
JOIN AdoptionEvents e ON p.EventID = e.EventID
WHERE e.EventID = 4;

-- 7th Query
DELIMITER //
CREATE PROCEDURE UpdateShelterInfo(
    IN shelter_id INT,
    IN new_name VARCHAR(100),
    IN new_location VARCHAR(255)
)
BEGIN
    -- Check if the shelter exists
    IF (SELECT COUNT(*) FROM Shelters WHERE ShelterID = shelter_id) = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Shelter ID does not exist.';
    ELSE
        -- Update the shelter information
        UPDATE Shelters
        SET Name = new_name, Location = new_location
        WHERE ShelterID = shelter_id;
    END IF;
END;
//
DELIMITER ;

CALL UpdateShelterInfo(3, 'Happy Paws Shelter', 'Bangalore, India');

-- 8th query

INSERT INTO Donations (ShelterID, DonorName, DonationType, DonationAmount, DonationItem) VALUES
(1, 'Ramesh Iyer', 'Cash', 3000, NULL),
(1, 'Pooja Rao', 'Item', NULL, 'Dog food, Beds'),
(2, 'Arjun Nair', 'Cash', 5000, NULL),
(2, 'Meera Kapoor', 'Item', NULL, 'Pet toys, Medications'),
(3, 'Suresh Babu', 'Cash', 2000, NULL),
(3, 'Anjali Jain', 'Item', NULL, 'Cat litter, Scratch posts'),
(4, 'Rajesh Verma', 'Cash', 7000, NULL),
(4, 'Neeta Singh', 'Item', NULL, 'Dog leashes, Training pads'),
(5, 'Kavita Das', 'Cash', 4500, NULL),
(5, 'Sanjay Kumar', 'Item', NULL, 'Bird cages, Animal food'),
(6, 'Vikrant Malhotra', 'Cash', 6500, NULL),
(6, 'Shweta Sharma', 'Item', NULL, 'Veterinary supplies'),
(7, 'Ayesha Khan', 'Cash', 8000, NULL),
(7, 'Gopal Mishra', 'Item', NULL, 'Pet accessories'),
(8, 'Nitin Joshi', 'Cash', 9000, NULL),
(8, 'Sunita Pillai', 'Item', NULL, 'Shelter cleaning supplies'),
(9, 'Harish Patel', 'Cash', 10000, NULL),
(9, 'Divya Reddy', 'Item', NULL, 'Pet adoption kits'),
(10, 'Farhan Shaikh', 'Cash', 12000, NULL),
(10, 'Monika Bansal', 'Item', NULL, 'Pet grooming kits');

UPDATE Donations
SET DonationMonthYear = DATE_FORMAT(DonationDate, '%M %Y');

SELECT s.Name AS ShelterName, 
       COALESCE(SUM(d.DonationAmount), 0) AS TotalDonation
FROM Shelters s
LEFT JOIN Donations d ON s.ShelterID = d.ShelterID
GROUP BY s.ShelterID, s.Name;

-- 9th query
SELECT Name, Age, Breed, Type 
FROM Pets
WHERE OwnerID IS NULL;

-- 10 th Query
SELECT DonationMonthYear, 
       COALESCE(SUM(DonationAmount), 0) AS TotalDonation
FROM Donations
GROUP BY DonationMonthYear
ORDER BY STR_TO_DATE(DonationMonthYear, '%M %Y');

-- 11th Query
SELECT DISTINCT Breed 
FROM Pets
WHERE Age BETWEEN 1 AND 3 
   OR Age > 5;
   
-- 12th Query

SELECT p.Name AS PetName, p.Age, p.Breed, p.Type, s.Name AS ShelterName, s.Location
FROM Pets p
JOIN Shelters s ON p.ShelterID = s.ShelterID
WHERE p.AvailableForAdoption = 1;

-- 13th Query
UPDATE AdoptionEvents
SET Location = 'Chennai, Tamil Nadu'
WHERE EventID = 5;

SELECT COUNT(ParticipantID) AS TotalParticipants
FROM Participants
WHERE EventID IN (
    SELECT EventID FROM AdoptionEvents WHERE Location LIKE 'Chennai%'
);

-- 14th Query
SELECT DISTINCT Breed
FROM Pets
WHERE Age BETWEEN 1 AND 5;

-- 15th Query
SELECT PetID, Name, Age, Breed, Type, AvailableForAdoption
FROM Pets
WHERE OwnerID IS NULL;

-- 16th Query
SELECT p.Name AS PetName, pa.ParticipantName AS AdopterName
FROM Adoptions a
JOIN Pets p ON a.PetID = p.PetID
JOIN Participants pa ON a.ParticipantID = pa.ParticipantID;

-- 17th Query
SELECT s.Name AS ShelterName, COUNT(p.PetID) AS AvailablePets
FROM Shelters s
LEFT JOIN Pets p ON s.ShelterID = p.ShelterID AND p.AvailableForAdoption = 1
GROUP BY s.ShelterID, s.Name;

-- 18thQuery
INSERT INTO Pets (Name, Age, Breed, Type, AvailableForAdoption, ShelterID) VALUES
('Max', 3, 'Indian Pariah', 'Dog', 1, 1), 
('Charlie', 2, 'Labrador Retriever', 'Dog', 1, 2),  
('Whiskers', 4, 'Persian', 'Cat', 1, 3),  
('Bella', 5, 'Golden Retriever', 'Dog', 1, 4),  
('Snowball', 2, 'Siamese', 'Cat', 1, 5);

SELECT p1.PetID AS Pet1_ID, p1.Name AS Pet1_Name, 
       p2.PetID AS Pet2_ID, p2.Name AS Pet2_Name, 
       p1.Breed, s.Name AS ShelterName
FROM Pets p1
JOIN Pets p2 ON p1.ShelterID = p2.ShelterID 
             AND p1.Breed = p2.Breed 
             AND p1.PetID < p2.PetID
JOIN Shelters s ON p1.ShelterID = s.ShelterID;

-- 19th Query
SELECT s.Name AS ShelterName, e.EventName AS EventName
FROM Shelters s
CROSS JOIN AdoptionEvents e;

-- 20th Query
SELECT * FROM Pets; 
INSERT INTO Adoptions (PetID, ParticipantID, AdoptionDate) VALUES
(11, 3, '2026-02-10 14:00:00'),
(12, 5, '2026-03-15 09:30:00'),
(13, 7, '2026-04-20 11:45:00'),
(14, 9, '2026-05-25 16:10:00'),
(15, 3, '2026-06-30 13:20:00');


SELECT s.ShelterID, s.Name AS ShelterName, COUNT(a.AdoptionID) AS TotalAdoptions
FROM Adoptions a
JOIN Pets p ON a.PetID = p.PetID
JOIN Shelters s ON p.ShelterID = s.ShelterID
GROUP BY s.ShelterID, s.Name
ORDER BY TotalAdoptions DESC
LIMIT 1;







