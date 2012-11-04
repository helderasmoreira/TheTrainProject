class TicketRoute < ActiveRecord::Base
  belongs_to :ticket
  belongs_to :route
  belongs_to :stop_start, :class_name => "Stop"
  belongs_to :stop_end, :class_name => "Stop"
  attr_accessible :date, :ticket_id, :route_id, :stop_start_id, :stop_end_id
end
