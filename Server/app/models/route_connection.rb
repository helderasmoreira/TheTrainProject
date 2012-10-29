class RouteConnection < ActiveRecord::Base
  belongs_to :route1
  belongs_to :route2
  belongs_to :stop
  attr_accessible :route1_id, :route2_id, :stop_id
end
