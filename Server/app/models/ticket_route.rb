class TicketRoute < ActiveRecord::Base
  belongs_to :ticket
  belongs_to :route
  attr_accessible :date, :ticket_id, :route_id, :stop_start_order, :stop_end_order
end
