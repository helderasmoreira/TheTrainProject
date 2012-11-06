class Ticket < ActiveRecord::Base
  	belongs_to :route
	belongs_to :user
	has_many :ticket_routes
  	attr_accessible :paid, :price, :user_id, :departureTime, :arrivalTime, :duration, :departure, :arrival
end
