class Ticket < ActiveRecord::Base
  	belongs_to :route
	belongs_to :user
	belongs_to :stop_start, :class_name => "Stop"
	belongs_to :stop_end, :class_name => "Stop"
  attr_accessible :date, :paid, :price, :route_id, :user_id, :stop_start_id, :stop_end_id
end
