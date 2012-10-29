class RouteConnectionsController < ApplicationController
  # GET /route_connections
  # GET /route_connections.json
  def index
    @route_connections = RouteConnection.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @route_connections }
    end
  end

  # GET /route_connections/1
  # GET /route_connections/1.json
  def show
    @route_connection = RouteConnection.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @route_connection }
    end
  end

  # GET /route_connections/new
  # GET /route_connections/new.json
  def new
    @route_connection = RouteConnection.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @route_connection }
    end
  end

  # GET /route_connections/1/edit
  def edit
    @route_connection = RouteConnection.find(params[:id])
  end

  # POST /route_connections
  # POST /route_connections.json
  def create
    @route_connection = RouteConnection.new(params[:route_connection])

    respond_to do |format|
      if @route_connection.save
        format.html { redirect_to @route_connection, notice: 'Route connection was successfully created.' }
        format.json { render json: @route_connection, status: :created, location: @route_connection }
      else
        format.html { render action: "new" }
        format.json { render json: @route_connection.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /route_connections/1
  # PUT /route_connections/1.json
  def update
    @route_connection = RouteConnection.find(params[:id])

    respond_to do |format|
      if @route_connection.update_attributes(params[:route_connection])
        format.html { redirect_to @route_connection, notice: 'Route connection was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @route_connection.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /route_connections/1
  # DELETE /route_connections/1.json
  def destroy
    @route_connection = RouteConnection.find(params[:id])
    @route_connection.destroy

    respond_to do |format|
      format.html { redirect_to route_connections_url }
      format.json { head :no_content }
    end
  end
end
