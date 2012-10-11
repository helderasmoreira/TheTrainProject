class Route < ActiveRecord::Base
	has_many :route_stops
  attr_accessible :ends, :starts
end
