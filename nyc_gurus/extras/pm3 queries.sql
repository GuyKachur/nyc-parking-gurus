-- Whats the average distance(in m) to a violation from the 5th street slope garden club?

select name,
       avg(getDistance(Latitude, Longitude, lat2, lon2)) as avgdistance
from communitygarden
         cross join
     (select latitude  as lat2,
             longitude as lon2
      from violation
      where Latitude is not null
        and longitude is not null
         -- if this is limited it goes much faster as cross joins can be huge
     ) as test
where name like '5th Street Slope Garden Club';

-- how far are the closest 10 pieces of graffiti from agrosons market
# select `ACCOUNT NAME`, getDistance(latitude, longitude, lat2, lon2) as distance
# from parking_gurus_wholesale_markets
#          cross join (select latitude  as lat2,
#                             longitude as lon2
#                      from graffiti
#                      where Latitude is not null
#                        and longitude is not null
# ) as graffiti
# where `ACCOUNT NAME` like 'BIG FARM CORP.'
# and LATITUDE is not null and LONGITUDE is not null
# order by distance
# limit 1;

-- What is the average distance to the closest incident involving a death for the top ten most expensive airbnb
select name, avg(getDistance(lat, lng, latitude, longitude)) as distance
from (
         select name, latitude as lat, longitude as lng, price
         from airbnb
         order by price desc
         limit 10
     ) as air
         cross join (
    select latitude, longitude, killed
    from (select LATITUDE,
                 LONGITUDE,
                 `PEDESTRIANS KILLED` + `PERSONS KILLED` + `MOTORISTS KILLED` + `CYCLISTS KILLED` as KILLED
          from parking_gurus.collisions
          where Latitude is not null
            and Longitude is not null) as pre
    where killed > 0
) as danger
group by name
order by distance;

-- what are the total number of people injured and killed from collisions per borough?
select BOROUGH, sum(INJURED) as TotalInjured,
       sum(KILLED) as TotalKilled,
       sum(INJURED + KILLED) as TOTAl
       from (
select BOROUGH,
       `PEDESTRIANS INJURED` + `PERSONS INJURED` + `MOTORISTS INJURED` + `CYCLISTS INJURED` as INJURED,
       `PEDESTRIANS KILLED` + `PERSONS KILLED` + `MOTORISTS KILLED` + `CYCLISTS KILLED` as KILLED
from parking_gurus.collisions) as pre
group by BOROUGH
order by total desc;
