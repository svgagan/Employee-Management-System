CREATE TABLE `ate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) DEFAULT NULL,
  `managerId` int(11) DEFAULT NULL,
  `directoryId` int(11) DEFAULT NULL,
  `ateStatus` bit(1) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `directorydetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `directoryName` varchar(500) DEFAULT NULL,
  `managerId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  `accessibleIds` varchar(1000) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `ateIds` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `managerId_for_directory` (`managerId`),
  KEY `permissionId_for_directory` (`permissionId`),
  CONSTRAINT `managerId_for_directory` FOREIGN KEY (`managerId`) REFERENCES `registration` (`id`),
  CONSTRAINT `permissionId_for_directory` FOREIGN KEY (`permissionId`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

CREATE TABLE `employeedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `divisionName` varchar(255) DEFAULT NULL,
  `supervisor` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id_for_employeeDetails` (`employeeId`),
  CONSTRAINT `employee_id_for_employeeDetails` FOREIGN KEY (`employeeId`) REFERENCES `registration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `employeedocs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `docName` varchar(255) DEFAULT NULL,
  `docFile` longblob,
  `docType` varchar(255) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `registrationId` int(11) NOT NULL,
  `directoryId` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directory_id_for_employeeDocs` (`directoryId`),
  KEY `registrationId_for_documents` (`registrationId`),
  CONSTRAINT `directory_id_for_employeeDocs` FOREIGN KEY (`directoryId`) REFERENCES `directorydetails` (`id`),
  CONSTRAINT `registrationId_for_documents` FOREIGN KEY (`registrationId`) REFERENCES `registration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

CREATE TABLE `leavedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `managerId` int(11) NOT NULL,
  `details` varchar(5000) DEFAULT NULL,
  `requestStatus` bit(1) DEFAULT NULL,
  `response` varchar(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employeeId_for_leaveDetails` (`employeeId`),
  KEY `managerId_for_leaves` (`managerId`),
  CONSTRAINT `employeeId_for_leaveDetails` FOREIGN KEY (`employeeId`) REFERENCES `registration` (`id`),
  CONSTRAINT `managerId_for_leaves` FOREIGN KEY (`managerId`) REFERENCES `registration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `leaves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leaveNum` int(11) DEFAULT NULL,
  `registrationId` int(11) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `registration_id_for_leaves` (`registrationId`),
  CONSTRAINT `registration_id_for_leaves` FOREIGN KEY (`registrationId`) REFERENCES `registration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registrationId` int(11) NOT NULL,
  `accountNum` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `bonus` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `registration_id_for_payment` (`registrationId`),
  CONSTRAINT `registration_id_for_payment` FOREIGN KEY (`registrationId`) REFERENCES `registration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `registration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int(11) NOT NULL,
  `managerstatus` bit(1) DEFAULT NULL,
  `managerId` int(11) DEFAULT NULL,
  `activitystatus` bit(1) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_for_registration` (`role`),
  CONSTRAINT `role_for_registration` FOREIGN KEY (`role`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

