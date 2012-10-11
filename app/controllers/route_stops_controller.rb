class RouteStopsController < ApplicationController
  # GET /route_stops
  # GET /route_stops.json
  def index
    @route_stops = RouteStop.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @route_stops }
    end
  end

  # GET /route_stops/1
  # GET /route_stops/1.json
  def show
    @route_stop = RouteStop.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @route_stop }
    end
  end

  # GET /route_stops/new
  # GET /route_stops/new.json
  def new
    @route_stop = RouteStop.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @route_stop }
    end
  end

  # GET /route_stops/1/edit
  def edit
    @route_stop = RouteStop.find(params[:id])
  end

  # POST /route_stops
  # POST /route_stops.json
  def create
    @route_stop = RouteStop.new(params[:route_stop])

    respond_to do |format|
      if @route_stop.save
        format.html { redirect_to @route_stop, notice: 'Route stop was successfully created.' }
        format.json { render json: @route_stop, status: :created, location: @route_stop }
      else
        format.html { render action: "new" }
        format.json { render json: @route_stop.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /route_stops/1
  # PUT /route_stops/1.json
  def update
    @route_stop = RouteStop.find(params[:id])

    respond_to do |format|
      if @route_stop.update_attributes(params[:route_stop])
        format.html { redirect_to @route_stop, notice: 'Route stop was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @route_stop.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /route_stops/1
  # DELETE /route_stops/1.json
  def destroy
    @route_stop = RouteStop.find(params[:id])
    @route_stop.destroy

    respond_to do |format|
      format.html { redirect_to route_stops_url }
      format.json { head :no_content }
    end
  end
end
