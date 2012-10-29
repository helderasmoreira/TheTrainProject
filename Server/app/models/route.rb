class Route < ActiveRecord::Base
	has_many :route_stops
	has_many :tickets
  attr_accessible :ends, :starts
end
