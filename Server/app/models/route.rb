class Route < ActiveRecord::Base
	has_many :route_stops
	has_many :tickets
	has_many :ticket_routes
  	attr_accessible :ends, :starts
end
