DROP SCHEMA IF EXISTS NAS;
/*CREATE SCHEMA IF NOT EXISTS NAS;*/
CREATE DATABASE `NAS` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE NAS;

create table automatedBootstrapConfig
(
URL_NAS varchar(255) not null,
serverName varchar(255) not null,
userName varchar(255) not null,
password varchar(255) not null,
remoteDirPath varchar(255) not null,
saveDirPath varchar(255) not null,
CONSTRAINT PK_BootstrapConfig PRIMARY KEY (userName)
);

insert into automatedBootstrapConfig values
('http://localhost:8084', 'ftp.drivehq.com', 'jeremylimys', 'scrabs123', '/2018/20180910', 'C:\\Users\\jeremylimys93\\Desktop');

create table users
(
username varchar(255) not null,
password varchar(255) not null,
accountType varchar(255) not null,
email varchar(255) not null,
creationDate varchar(255) not null,
token varchar(255),
tokenExpiration varchar(255),
CONSTRAINT PK_users PRIMARY KEY (username)
);

insert into users values
('admin', '$2a$12$jlK76MxPP7qWy0RpJ528qeFckE30Nwt34vuHBd9mZkAg2lX06jsb6', 'Admin', 'gslim.2016@sis.smu.edu.sg', '2019-02-26 00:41:00',null,null),
('jasper', '$2a$12$i4TVHWuB0Rz76NxAVjH31eZercV3PrSOvrVr05qpvdxBijfqbVQYe', 'User', 'gslim.2016@sis.smu.edu.sg', '2019-02-26 00:41:00',null,null),
('scrabstester', '$2a$12$Ac8eN95j4NB1xkcSdT/L2ehh10dqpegM96e7/JLIfqNI2gXNqrth2', 'User', 'gslim.2016@sis.smu.edu.sg', '2019-02-26 00:41:00',null,null),
('engineer1', '$2a$12$cxn/ygNovC0dyy1Yu7zsIOQfC.OCK35X7ZNwD.OApvb6jN0R2wjS2', 'User', 'gslim.2016@sis.smu.edu.sg', '2019-02-26 00:41:00',null,null);

create table alert
(
alertId int not null AUTO_INCREMENT,
username varchar(255) not null,
categoryDate varchar(255) not null,
startDate varchar(255) not null,
endDate varchar(255) not null,
lessThanConnectedClients varchar(255) not null,
moreThanConnectedClients varchar(255) not null,
lessThanUtilization varchar(255) not null,
moreThanUtilization varchar(255) not null,
outlierUtilizationRates varchar(255) not null,
outlierConnectedClients varchar(255) not null,
CONSTRAINT PK_Alert PRIMARY KEY (alertId),
CONSTRAINT FK_Alert FOREIGN KEY (username) REFERENCES users (username)
);

create table bootstrapHistory
(
uploadTime varchar(20) not null,
logFileName varchar(80) not null,
operator varchar(20) not null,
logDateTime varchar(20) not null,
totalRecords varchar(20) not null default "N.A",
m1Sessions varchar(20) not null default "N.A",
singtelSessions varchar(20) not null default "N.A",
starhubSessions varchar(20) not null default "N.A",
totalSessions varchar(20) not null default "N.A",
outcome varchar(30) not null,
CONSTRAINT PK_bootstrapHistory PRIMARY KEY (uploadTime,logFileName,operator, logDateTime,outcome)
);

create table bootstrapTotalRecords
(uploadTime varchar(75) not null,
totalRecords varchar(20) not null default "N.A",
m1Sessions varchar(20) not null default "N.A",
singtelSessions varchar(20) not null default "N.A",
starhubSessions varchar(20) not null default "N.A",
totalSessions varchar(20) not null default "N.A",
CONSTRAINT PK_bootstrapTotalRecords PRIMARY KEY (uploadTime,totalRecords,m1Sessions,singtelSessions,starhubSessions,totalSessions)
);


create table rawSessionEntry
(
uuid varchar(40) not null ,
acctStatusType varchar(20) not null,
acctSessionId varchar(100) not null,
userName varchar(150) not null,
nasIdentifier varchar(100) not null,
calledStationId varchar(100) not null,
callingStationId varchar(100) not null,
ruckusSSID varchar(100) not null,
acctDelayTime varchar(100) not null,
timestamp varchar(255) not null,
connectInfo varchar(255) not null,
classs varchar(255) not null,
tunnelPrivateGroupID varchar(255) not null,
acctSessionTime varchar(255) not null,
acctInputPackets varchar(255) not null,
acctOutputPackets varchar(255) not null,
acctInputOctets varchar(255) not null,
acctOutputOctets varchar(255) not null,
acctTerminateCause varchar(255) not null,
acctMultiSessionId varchar(255) not null,
acctLinkCount varchar(255) not null,
acctAuthentic varchar(255) not null,
airespaceWLANId varchar(20) not null,
arubaEssidName varchar(20) not null,
arubaLocationId varchar(20) not null,
arubaUserRole varchar(20) not null,
arubaUserVlan varchar(20) not null,
ruckusStaRSSI varchar(20) not null,
ssid varchar(255) not null,
operatorRadius varchar(255) not null,
uploadTime varchar(255) not null,
authMethod varchar(255) not null,
accountOperator varchar(255) not null,
logFileName varchar(255) not null default '',
CONSTRAINT PK_Raw PRIMARY KEY (uuid,uploadTime)
);

create table errorSessionEntry
(
uuid varchar(40) not null ,
acctStatusType varchar(20) not null,
acctSessionId varchar(100) not null,
userName varchar(150) not null,
nasIdentifier varchar(100) not null,
calledStationId varchar(100) not null,
callingStationId varchar(100) not null,
ruckusSSID varchar(100) not null,
acctDelayTime varchar(100) not null,
timestamp varchar(255) not null,
connectInfo varchar(255) not null,
classs varchar(255) not null,
tunnelPrivateGroupID varchar(255) not null,
acctSessionTime varchar(255) not null,
acctInputPackets varchar(255) not null,
acctOutputPackets varchar(255) not null,
acctInputOctets varchar(255) not null,
acctOutputOctets varchar(255) not null,
acctTerminateCause varchar(255) not null,
acctMultiSessionId varchar(255) not null,
acctLinkCount varchar(255) not null,
acctAuthentic varchar(255) not null,
airespaceWLANId varchar(20) not null,
arubaEssidName varchar(20) not null,
arubaLocationId varchar(20) not null,
arubaUserRole varchar(20) not null,
arubaUserVlan varchar(20) not null,
ruckusStaRSSI varchar(20) not null,
ssid varchar(255) not null,
operatorRadius varchar(255) not null,
uploadTime varchar(255) not null,
authMethod varchar(255) not null,
accountOperator varchar(255) not null,
logFileName varchar(255) not null default '',
erroneousVariables varchar(255) not null default '',
ignoreData varchar(255) not null default '0',
CONSTRAINT PK_error PRIMARY KEY (uuid,uploadTime)
);

create table cleanSessionEntry
(
uuid varchar(40) not null ,
acctStatusType varchar(20) not null,
acctSessionId varchar(100) not null,
userName varchar(150) not null,
nasIdentifier varchar(100) not null,
calledStationId varchar(100) not null,
callingStationId varchar(100) not null,
ruckusSSID varchar(100) not null,
acctDelayTime varchar(100) not null,
timestamp varchar(255) not null,
connectInfo varchar(255) not null,
classs varchar(255) not null,
tunnelPrivateGroupID varchar(255) not null,
acctSessionTime varchar(255) not null,
acctInputPackets varchar(255) not null,
acctOutputPackets varchar(255) not null,
acctInputOctets varchar(255) not null,
acctOutputOctets varchar(255) not null,
acctTerminateCause varchar(255) not null,
acctMultiSessionId varchar(255) not null,
acctLinkCount varchar(255) not null,
acctAuthentic varchar(255) not null,
airespaceWLANId varchar(20) not null,
arubaEssidName varchar(20) not null,
arubaLocationId varchar(20) not null,
arubaUserRole varchar(20) not null,
arubaUserVlan varchar(20) not null,
ruckusStaRSSI varchar(20) not null,
ssid varchar(255) not null,
operatorRadius varchar(255) not null,
uploadTime varchar(255) not null,
authMethod varchar(255) not null,
accountOperator varchar(255) not null,
logFileName varchar(255) not null default '',
INDEX (timestamp),
CONSTRAINT PK_Raw PRIMARY KEY (uuid,uploadTime)

);


create table auditLog
(
timestamp varchar(20) not null,
userName varchar(50) not null,
actionType varchar(255) not null,
action varchar(255) not null,
details varchar(255) not null,
CONSTRAINT PK_audit PRIMARY KEY (userName, timestamp)
);

create table hotspotDatabase
(
apId varchar(100) not null,
reportMonth varchar(255) not null,
reportYear varchar(255) not null,
operator varchar(255) not null, 
deploymentType varchar(255) not null, 
locationType varchar(100) not null, 
locationName varchar(100) not null, 
streetAddress varchar(255) not null, 
block varchar(20) not null, 
level varchar(255) not null, 
unit varchar(255) not null, 
description varchar(255) not null, 
postalCode varchar(255) not null, 
apCount varchar(255) not null, 
status varchar(255) not null, 
lattitude varchar(255) not null, 
longitude varchar(255) not null, 
backhaulId varchar(255) not null, 
macAddress varchar(255) not null, 
bssid_1 varchar(255), 
bssid_2 varchar(255), 
bssid_3 varchar(255), 
bssid_4 varchar(255), 
category varchar(255), 
hotspotAvaliability varchar(255) not null,
apBrandModel varchar(255) not null, 
dateUpdated varchar(255) not null,
CONSTRAINT PK_hotspotDb PRIMARY KEY (apId,locationName,block)
);

ALTER TABLE hotspotDatabase CONVERT TO CHARACTER SET utf8;

create table backhaulDatabase
(
backhaulId varchar(50) not null, 
status varchar(255) not null, 
deploymentType varchar(255) not null, 
postalCode varchar(255) not null, 
backhaulSize varchar(255) not null, 
operator varchar(255) not null,
CONSTRAINT PK_backhaulDb PRIMARY KEY (backhaulId)
);